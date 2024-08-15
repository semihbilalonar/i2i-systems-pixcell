//
//  SignUp.swift
//  i2iProject
//
//  Created by Engin Gündüz on 7.08.2024.
//

import SwiftUI

struct SignUpView: View {
    @State var txtName: String = ""
    @State var txtSurname: String = ""
    @State var txtEmail: String = ""
    @State var txtPhoneNumber: String = ""
    @State var txtPassword: String = ""
    @State var txtConfirmPassword: String = ""
    @State var isRemember: Bool = true
    @State var animate: Bool = false
    let options = ["Package 1", "Package 2", "Package 3"]
    @State private var selectedOption = "Option 1"
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.gray80.edgesIgnoringSafeArea(.all)
                VStack {
                    Text("PixCell")
                        .font(.largeTitle)
                        .foregroundColor(Color.white)
                        .fontWeight(.bold)
                        .padding(.top, .topInsets + 30)
                    
                    Spacer()
                    
                    RoundTextFieldSignUp(title: "Name", text: $txtName, keyBoardType: .default)
                        .padding(.horizontal, 20)
                        
                    RoundTextFieldSignUp(title: "Surname", text: $txtSurname, keyBoardType: .default)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Email", text: $txtEmail, keyBoardType: .emailAddress)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Phone Number", text: $txtPhoneNumber, keyBoardType: .phonePad)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Password", text: $txtPassword, isPassword: true)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Security Key", text: $txtConfirmPassword, isPassword: true)
                        .padding(.horizontal, 20)
                    
                    //Menu
                    Text("Select a package that suit you best.")
                        .font(.customFont(.regular, fontSize: 15))
                        .foregroundColor(Color.gray30)
                        .padding(.top)
                        .padding(.horizontal, 18)
                        .padding(.bottom, 15)
                    
                    Menu {
                        ForEach(options, id: \.self) { option in
                            Button(action: {
                                selectedOption = option
                            }) {
                                Text(option)
                            }
                        }
                    } label: {
                        // Menüye tıklama düğmesi
                        Text(selectedOption)
                            .foregroundColor(.gray50)
                            .padding()
                            .background(.white.opacity(0.9))
                            .cornerRadius(10)
                    }
                    
                    NavigationLink {
                        // Sign up action
                        EmptyView()
                            .navigationBarBackButtonHidden(true)
                    } label: {
                        Text("Sign Up")
                            .foregroundColor(.white)
                            .font(.headline)
                            .frame(height: 55)
                            .frame(maxWidth: .infinity)
                            .background(animate ? Color.accentColor : Color.accentColor)
                            .clipShape(Capsule())
                            .cornerRadius(14)
                    }
                    .padding(.top, 20)
                    .padding(.bottom, 90)
                    .padding(.horizontal, animate ? 30 : 50)
                    .shadow(
                        color: animate ? Color.accentColor.opacity(0.7) : Color.accentColor.opacity(0.7),
                        radius: animate ? 30 : 10,
                        x: 0,
                        y: animate ? 50 : 30
                    )
                    .scaleEffect(animate ? 1.1 : 1.0)
                    .offset(y: animate ? -7 : 0)
                    
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
    SignUpView()
}
