import Foundation
import common

// TODOs:
// 1. Implement remaining Android tests
// 2. Clean up code.
// 1a. Add additional logging to look into dispatch to main/background thread. Think of cheat test.
// 3. Prepare code for multiple runs

// 4. Test on device.
// 5. Test with Performance analyze tools (Xcode)
// 6. Write a post ?

class PerformanceModel: ObservableObject {
    private let queue = DispatchQueue(label: "com.app.concurrentQueue", attributes: .concurrent)

    func onMainThread() {
//        measure {
//            ThreadPerformance().onMainThread()
//        }
        let startDate = logCurrentTime(note: "Pre dispatch")
        ThreadPerformance().doSomeWork {
            let endDate = Date()
            let dateInterval = DateInterval(start: startDate, end: endDate)
            print("Android is done \(dateInterval.duration)")
        }
        compute()
        let endDate = Date()
        let dateInterval = DateInterval(start: startDate, end: endDate)
        print("Android main thread done \(dateInterval.duration)")
    }

    func singleTask(onComplete: @escaping () -> Void) {
        // For running compute on background thread
        // let _ = logCurrentTime(note: "Pre dispatch")
        DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
            self.compute()
            DispatchQueue.main.async {
                onComplete()
            }
        }
        // let _ = logCurrentTime(note: "Post dispatch")
    }

    func measureThat() {
        // let startDate = Date()
        let startDate = logCurrentTime(note: "Pre dispatch")
        singleTask {
            let endDate = Date()
            let dateInterval = DateInterval(start: startDate, end: endDate)
            print("iOS is done \(dateInterval.duration)")
        }
        compute()
        let endDate = Date()
        let dateInterval = DateInterval(start: startDate, end: endDate)
        print("iOS main Thread done \(dateInterval.duration)")
    }

    func singleTaskOnMultipleThreads() {
        // For running compute on multiple background threads
        let _ = logCurrentTime(note: "Pre loop")
        for i in 0...5 {
            let _ = logCurrentTime(note: "Pre dispatch \(i)")
            queue.async { [unowned self] in
                self.compute()
            }
            let _ = logCurrentTime(note: "Post dispatch \(i)")
        }
    }

    func multipleTaskOnSingleBackgroundThread() {
        // For running compute multiple times on background thread
        let _ = logCurrentTime(note: "Pre loop")
        for i in 0...5 {
            let _ = logCurrentTime(note: "Pre dispatch \(i)")
            DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                self.compute()
            }
            let _ = logCurrentTime(note: "Post dispatch")
        }
    }

    func networkCall() {
        // For Ferran
    }

    private func compute() {
        SwiftThreadPerformance(size: 5000).compute()
    }

    func logCurrentTime(note: String) -> Date {
        let startDate = Date()
        print("\(note) time: \(DateFormatter.logTimeFormatter().string(from: startDate))")
        return startDate
    }

    func measure(function: () -> Void) {
        // on main thread
        // on background threads
        let startDate = Date()

        self.compute()

        let endDate = Date()
        let dateInterval = DateInterval(start: startDate, end: endDate)
        print("Duration \(dateInterval.duration)")
    }
}

extension DateFormatter {
    static func logTimeFormatter() -> DateFormatter {
        let df = DateFormatter()
        df.dateFormat = "H:m:ss.SSSS"
        return df
    }
}

class SwiftThreadPerformance {

    let testArray: [Int]
    private let queue = DispatchQueue(label: "com.app.concurrentQueue", attributes: .concurrent)

    init(size: Int) {
        testArray = Array(0...size)
    }

    func compute() {
        let _ = bubbleSort(of: testArray)
    }

    private func bubbleSort(of array: [Int]) -> [Int] {
        var sorted = array
        var didSwitch = true
        while (didSwitch) {
            didSwitch = false
            for currentIndex in 1...array.count-1 {
                let shouldSwitch = sorted[currentIndex] > sorted[currentIndex - 1]
                if shouldSwitch {
                    let temp = sorted[currentIndex - 1]
                    sorted[currentIndex - 1] = sorted[currentIndex]
                    sorted[currentIndex] = temp
                    didSwitch = true
                }
            }
        }
        return sorted
    }
}
