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

// Additional notes
// should we consider injecting compute to android?

protocol TestThreadPerformance {
    func testSingleTaskOnSingleBackgroundThread()
    func testMultipleTaskOnMultipleBackgroundThread()
    func testSequentialTasksOnSingleBackgrounThread()
}

enum Platform {
    case iOS
    case android
}

class PerformanceModel: ObservableObject {
    private let queue = DispatchQueue(label: "com.app.concurrentQueue", attributes: .concurrent)
    private let swiftThreadPerformance = SwiftThreadPerformance(size: 200)
    private let androidThreadPerformance = AndroidThreadPerformance(size: 200)
    @Published var currentPlatform: Platform = .iOS

    private var performanceTester: TestThreadPerformance {
        switch currentPlatform {
        case .iOS:
            return swiftThreadPerformance
        case .android:
            return androidThreadPerformance
        }
    }

    func iosTesting() {
        currentPlatform = .iOS
    }

    func androidTesting() {
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
        DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
            self.compute()
        }
    }

    func testMultipleTaskOnMultipleBackgroundThread() {
        for _ in 0...5 {
            queue.async { [unowned self] in
                self.compute()
            }
        }
    }

    func testSequentialTasksOnSingleBackgrounThread() {
        // Not sure if we need that?
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

extension AndroidThreadPerformance: TestThreadPerformance {}
