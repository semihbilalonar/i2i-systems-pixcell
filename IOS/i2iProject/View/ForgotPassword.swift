//
//  ForgotPassword.swift
//  i2iProject
//
//  Created by Engin Gündüz on 8.08.2024.
//

import SwiftUI

struct ForgotPassword: View {
    @State var txtSecurityKey: String = ""
    @State var txtPhoneNumber: String = ""
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
                                    
                    Text("Enter your phone number and security key to login.")
                        .font(.customFont(.regular, fontSize: 15))
                        .foregroundColor(Color.gray30)
                        .padding(.top, 5)
                        .padding(.bottom, 15)
                        .padding(.horizontal, 1)
                    
                    RoundTextField(title: "Phone Number", text: $txtPhoneNumber, keyBoardType: .emailAddress)
                        .padding(.horizontal, 20)
                        .padding(.bottom, 15)
                    RoundTextField(title: "Security Key", text: $txtSecurityKey, keyBoardType: .emailAddress)
                        .padding(.horizontal, 20)
//                        .padding(.top, )
                    
                    NavigationLink(destination: EmptyView()
                        .navigationBarBackButtonHidden(true)) {
                        Text("Go User Details")
                            .foregroundColor(.white)
                            .font(.headline)
                            .frame(height: 55)
                            .frame(maxWidth: .infinity)
                            .background(animate ? Color.accentColor : Color.accentColor)
                            .clipShape(Capsule())
                            .cornerRadius(14)
                    }
                    .padding(.top, 40)
                    .padding(.bottom, 150)
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
                .padding()
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
    ForgotPassword()
}
