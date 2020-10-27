import SwiftUI
import common

import SwiftUI

struct ContentView: View {
    var model: PerformanceModel

    var body: some View {
        VStack{
            HStack{
                Button(action: model.iosTesting) {
                    Spacer()
                    Text("iOS")
                        .multilineTextAlignment(.center)
                    Spacer()
                }
                .padding()
                .border(Color.blue, width: 1)
                Button(action: model.androidTesting) {
                    Spacer()
                    Text("Android")
                        .multilineTextAlignment(.center)
                    Spacer()
                }
                .padding()
                .border(Color.blue, width: 1)
            }
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
            Button(action: model.singleTaskOnSingleBackgroundThread) {
                Spacer()
                Text("Single Task On Single Background Thread")
                    .multilineTextAlignment(.center)
                Spacer()
            }
            .padding()
            .border(Color.blue, width: 1)

            Button(action: model.multipleTaskOnMultipleBackgroundThread) {
                Spacer()
                Text("Multiple Task On Multiple Background Thread")
                    .multilineTextAlignment(.center)
                Spacer()
            }
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
