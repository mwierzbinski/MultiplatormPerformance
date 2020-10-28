import Foundation
import common

/*
 TODOs:

 3. Prepare code for multiple runs
 5. Test with Performance analyze tools (Xcode)
 6. Write a post ?

 ---
 Can print all async tasks individually on Android instead of block completion time (as we do on iOS)
 Random array causes inconsistent results
 Android currently has issue that tests are running at the same time (not blocking)
 */

protocol TestThreadPerformance {
    func testSingleTaskOnSingleBackgroundThread()
    func testMultipleTaskOnMultipleBackgroundThread()
}

enum Platform {
    case iOS
    case android
}

class PerformanceModel: ObservableObject {
    private let queue = DispatchQueue(label: "com.app.concurrentQueue", attributes: .concurrent)
    private let swiftThreadPerformance = SwiftThreadPerformance(size: 2000)
    @Published var currentPlatform: Platform = .iOS

    private var performanceTester: TestThreadPerformance {
//        switch currentPlatform {
//        case .iOS:
            return swiftThreadPerformance
//        case .android:
//            return androidThreadPerformance
//        }
    }

    func iosTesting() {
        currentPlatform = .iOS
    }

    func androidTesting() {

        Greeting().greeting { text in
            print(text)
        }
        currentPlatform = .android
    }

    func singleTaskOnSingleBackgroundThread() {
        performanceTester.testSingleTaskOnSingleBackgroundThread()
    }

    func multipleTaskOnMultipleBackgroundThread() {
        performanceTester.testMultipleTaskOnMultipleBackgroundThread()
    }

    private func compute() {
        SwiftThreadPerformance(size: 5000).compute()
    }
}

extension DateFormatter {
    static func logTimeFormatter() -> DateFormatter {
        let df = DateFormatter()
        df.dateFormat = "H:m:ss.SSSS"
        return df
    }
}

class SwiftThreadPerformance: TestThreadPerformance {
    let testArray: [Int]
    private let queue = DispatchQueue(label: "com.app.concurrentQueue", attributes: .concurrent)

    init(size: Int) {
        testArray = Array(0...size)
    }

    func testSingleTaskOnSingleBackgroundThread() {
        let startTime = Date()
        DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
            self.compute()
            print(Date().timeIntervalSince(startTime))
        }
    }

    func testMultipleTaskOnMultipleBackgroundThread() {
        let startTime = Date()
        for i in 0...5 {
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
