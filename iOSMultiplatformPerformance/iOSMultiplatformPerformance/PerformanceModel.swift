import Foundation
import common

/*
 TODOs:

 1. Can print all async tasks individually on Android instead of block completion time (as we do on iOS)
 3. Prepare code for multiple runs
 5. Test with Performance analyze tools (Xcode)
 6. Write a post ?
 9. Disable buttons when test is running

 ---
 Talk with Ferran
 Random array causes inconsistent results
 Android currently has issue that tests are running at the same time (not blocking)
 NOTE: Seems like android is using main scope to run computation that is meant to be on background
 */

class PerformanceModel: ObservableObject {
    private let swiftThreadPerformance = SwiftThreadPerformance(size: 2000)

    func iosTesting() {
        swiftThreadPerformance.testSingleTaskOnSingleBackgroundThread {
            self.swiftThreadPerformance.testMultipleTaskOnMultipleBackgroundThread()
        }
    }

    func androidTesting() {
        let model = NativeViewModel()
        model.testAsync()
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

    func testSingleTaskOnSingleBackgroundThread(onComplete: @escaping () -> Void) {
        let startTime = Date()
        DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
            self.compute()
            print(Date().timeIntervalSince(startTime))
            DispatchQueue.main.async {
                onComplete()
            }
        }
    }

    func testMultipleTaskOnMultipleBackgroundThread() {
        let startTime = Date()
        for i in 0...7 {
            queue.async { [unowned self] in
                self.compute()
                print("\(i) - \(Date().timeIntervalSince(startTime))")
            }
        }
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
