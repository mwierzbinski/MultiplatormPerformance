import SwiftUI
import common

struct ContentView: View {
    var model: PerformanceModel

    var body: some View {
        VStack{
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
        .padding(20)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(model: PerformanceModel())
    }
}
