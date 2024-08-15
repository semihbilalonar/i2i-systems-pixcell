import SwiftUI

struct SignInView: View {
    @State var txtLogin: String = ""
    @State var txtPassword: String = ""
    @State var isRemember: Bool = true
    @State var animate: Bool = false
    
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
                    
                    Spacer()
                    
                    RoundTextField(title: "Phone Number", text: $txtLogin, keyBoardType: .emailAddress)
                        .padding(.horizontal, 20)
                        .padding(.bottom, 15)
                    RoundTextField(title: "Password", text: $txtPassword, isPassword: true)
                        .padding(.horizontal, 20)
                        .padding(.bottom, 20)
                    
                    HStack {
                        Button {
                            isRemember = !isRemember
                        } label: {
                            HStack {
                                Image(systemName: isRemember ? "checkmark.square" : "square")
                                    .resizable()
                                    .frame(width: 20, height: 20)
                                Text("Remember Me")
                                    .multilineTextAlignment(.center)
                                    .font(.customFont(.regular, fontSize: 14))
                            }
                            .foregroundColor(.gray50)
                        }
                        Spacer()
                        NavigationLink {
                            // Forgot Password action
                            ForgotPassword()
                        } label: {
                            Text("Forgot Password")
                                .multilineTextAlignment(.center)
                                .font(.customFont(.regular, fontSize: 14))
                        }
                        .foregroundColor(.gray50)
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 15)
                    
                    NavigationLink(destination: UserDetails()
                        .navigationBarBackButtonHidden(true)) {
                        Text("Sign In")
                            .foregroundColor(.white)
                            .font(.headline)
                            .frame(height: 55)
                            .frame(maxWidth: .infinity)
                            .background(animate ? Color.accentColor : Color.accentColor)
                            .clipShape(Capsule())
                            .cornerRadius(14)
                    }
                    .padding(.top, 20)
                    .padding(.bottom, 200)
                    .padding(.horizontal, animate ? 30 : 50)
                    .shadow(
                        color: animate ? Color.accentColor.opacity(0.7) : Color.accentColor.opacity(0.7),
                        radius: animate ? 30 : 10,
                        x: 0,
                        y: animate ? 50 : 30
                    )
                    .scaleEffect(animate ? 1.1 : 1.0)
                    .offset(y: animate ? -7 : 0)
                    
                    HStack {
                        Text("Don't have an account?")
                            .font(.subheadline)
                            .foregroundColor(.gray50)
                        
                        NavigationLink(destination: SignUpView().navigationBarBackButtonHidden(true)) {
                            Text("Sign Up")
                                .font(.headline)
                                .foregroundColor(.black)
                                .fontWeight(.bold)
                                .frame(width: 120, height: 45)
                                .background(.white)
                                .clipShape(Capsule())
                                .cornerRadius(14)
                        }
                        .padding(.leading)
                    }
                    
                }
                .navigationTitle("")
                .navigationBarBackButtonHidden(true)
                .navigationBarHidden(true)
                .onAppear(perform: addAnimation)
            }
        }
    }
    
    func addAnimation() {
        guard !animate else { return }
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            withAnimation(
                Animation
                    .easeInOut(duration: 2.0)
                    .repeatForever()
            ) {
                animate.toggle()
            }
        }
    }
}

#Preview {
    SignInView()
}
