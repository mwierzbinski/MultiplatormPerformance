import SwiftUI
import common

func greet() -> String {
    return Greeting().greeting()
}

import SwiftUI

struct ContentView: View {
    let model: PerformanceModel

    var body: some View {
        VStack{
            Button(action: model.onMainThread) {
                Spacer()
                Text("On Main Thread")
                    .multilineTextAlignment(.center)
                Spacer()
            }
                .padding()
                .border(Color.blue, width: 1)
            Button(action: model.measureThat) {
                Spacer()
                Text("Start computation in background")
                    .multilineTextAlignment(.center)
                Spacer()
            }
                .padding()
                .border(Color.blue, width: 1)

            Button("Start computations on multiple Threads",
                   action: model.singleTaskOnMultipleThreads)
                .multilineTextAlignment(.center)
                .padding()
                .border(Color.blue, width: 1)

            Button(action: model.multipleTaskOnSingleBackgroundThread) {
                Text("Start multiple computations on single thread")
            }
            .multilineTextAlignment(.center)
            .padding()
            .border(Color.blue, width: 1)

            Button(action: model.networkCall) {
                Spacer()
                Text("Network call")
                Spacer()
            }
            .multilineTextAlignment(.center)
            .padding()
            .border(Color.blue, width: 1)
        }
        .padding(20)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(model: PerformanceModel())
    }
}
