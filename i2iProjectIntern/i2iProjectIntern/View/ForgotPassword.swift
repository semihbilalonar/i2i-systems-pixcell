import SwiftUI

struct ForgotPassword: View {
    @State var txtPhoneNumber: String = ""
    @State var txtSecurityKey: String = ""
    @State var isAnimating: Bool = false
    @State var showAlert = false
    @State var alertMessage = ""
    @State var isLoggedIn = false

    var body: some View {
        NavigationView {
            ZStack {
                Color.gray80.edgesIgnoringSafeArea(.all)
                VStack {
                    Text("PixCell")
                        .font(.largeTitle)
                        .foregroundColor(Color.white)
                        .fontWeight(.bold)
                        .padding(.top, .topInsets + 10)
                    
                    Text("Enter your phone number and security key.")
                        .font(.customFont(.regular, fontSize: 15))
                        .foregroundColor(Color.gray30)
                        .padding(.top, 5)
                        .padding(.bottom, 15)
                    
                    RoundTextField(title: "Phone Number", text: $txtPhoneNumber, keyBoardType: .numberPad)
                        .padding(.horizontal, 20)
                        .padding(.bottom, 15)
                    
                    RoundTextField(title: "Security Key", text: $txtSecurityKey, isPassword: true)
                        .padding(.horizontal, 20)
                    
                    Button(action: {
                        forgotPassword()
                    }) {
                        Text("Submit")
                            .foregroundColor(.white)
                            .font(.headline)
                            .frame(height: 55)
                            .frame(maxWidth: .infinity)
                            .background(isAnimating ? Color.accentColor : Color.accentColor)
                            .clipShape(Capsule())
                            .cornerRadius(14)
                    }
                    .padding(.top, 40)
                    .padding(.bottom, 150)
                    .padding(.horizontal, isAnimating ? 30 : 50)
                    .shadow(
                        color: isAnimating ? Color.accentColor.opacity(0.7) : Color.accentColor.opacity(0.7),
                        radius: isAnimating ? 30 : 10,
                        x: 0,
                        y: isAnimating ? 50 : 30
                    )
                    .scaleEffect(isAnimating ? 1.1 : 1.0)
                    .offset(y: isAnimating ? -7 : 0)
                    
                    HStack {
                        Spacer()
                        NavigationLink(destination: SignInView()
                            .navigationBarBackButtonHidden(true)) {
                            Text("Back to Sign In")
                                .font(.customFont(.regular, fontSize: 14))
                                .foregroundColor(.gray50)
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 15)
                }
                .padding()
                .onAppear(perform: addAnimation)
                .alert(isPresented: $showAlert) {
                    Alert(title: Text("Error"), message: Text(alertMessage), dismissButton: .default(Text("OK")))
                }
                .background(
                    NavigationLink(destination: UserDetails(phoneNumber: txtPhoneNumber)
                        .navigationBarBackButtonHidden(true), isActive: $isLoggedIn) {
                        EmptyView()
                    }
                )
            }
        }
    }
    
    func forgotPassword() {
        guard let url = URL(string: "http://34.172.128.173/api/auth/forgotPassword/\(txtPhoneNumber)/\(txtSecurityKey)") else {
            self.alertMessage = "Invalid URL"
            self.showAlert = true
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    self.alertMessage = "Network Error"
                    self.showAlert = true
                }
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 {
                // Process response
                let responseString = String(data: data, encoding: .utf8)
                print("Response: \(responseString ?? "")")
                
                // Check for success message
                if responseString?.contains("Successful") == true {
                    // Successful response
                    DispatchQueue.main.async {
                        self.isLoggedIn = true
                    }
                } else {
                    DispatchQueue.main.async {
                        self.alertMessage = "Invalid Credentials"
                        self.showAlert = true
                    }
                }
            } else {
                DispatchQueue.main.async {
                    self.alertMessage = "Invalid Credentials"
                    self.showAlert = true
                }
            }
        }.resume()
    }

    func addAnimation() {
        guard !isAnimating else { return }
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            withAnimation(
                Animation
                    .easeInOut(duration: 2.0)
                    .repeatForever()
            ) {
                isAnimating.toggle()
            }
        }
    }
}

#Preview {
    ForgotPassword()
}
