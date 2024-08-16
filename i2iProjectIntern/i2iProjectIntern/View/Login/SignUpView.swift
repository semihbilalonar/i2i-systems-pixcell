import SwiftUI

struct SignUpView: View {
    @StateObject private var viewModel = SignUpViewModel()
    @State var txtName: String = ""
    @State var txtSurname: String = ""
    @State var txtEmail: String = ""
    @State var txtPhoneNumber: String = ""
    @State var txtPassword: String = ""
    @State var txtSecurityKey: String = ""
    @State var isRemember: Bool = true
    @State var animate: Bool = false
    @State private var isSignUpSuccessful = false  // Yeni state
    
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
                    
                    RoundTextFieldSignUp(title: "Phone Number", text: $txtPhoneNumber, keyBoardType: .phonePad)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Name", text: $txtName, keyBoardType: .default)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Surname", text: $txtSurname, keyBoardType: .default)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Email", text: $txtEmail, keyBoardType: .emailAddress)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Password", text: $txtPassword, isPassword: true)
                        .padding(.horizontal, 20)
                    RoundTextFieldSignUp(title: "Security Key", text: $txtSecurityKey)
                        .padding(.horizontal, 20)
                    
                    Text("Select a package that suits you best.")
                        .font(.customFont(.regular, fontSize: 15))
                        .foregroundColor(Color.gray30)
                        .padding(.top)
                        .padding(.horizontal, 18)
                        .padding(.bottom, 15)
                    
                    Menu {
                        ForEach(viewModel.packages, id: \.packageId) { package in
                            Button(action: {
                                viewModel.selectedOption = "\(package.packageName) - \(package.amountMinutes) mins, \(package.amountData) MB, \(package.amountSms) SMS, \(package.price) USD"
                                viewModel.selectedPackage = package
                                print("Selected package: \(package.packageName), ID: \(package.packageId)")
                            }) {
                                VStack(alignment: .leading) {
                                    Text(package.packageName)
                                        .fontWeight(.bold)
                                    Text("Minutes: \(package.amountMinutes), Data: \(package.amountData) MB, SMS: \(package.amountSms)")
                                    Text("Price: \(package.price) USD")
                                    Text("Period: \(package.period) days")
                                        .font(.subheadline)
                                        .foregroundColor(.gray)
                                }
                                .padding(.vertical, 5)
                            }
                        }
                    } label: {
                        Text(viewModel.selectedOption.isEmpty ? "Select a package" : viewModel.selectedOption)
                            .foregroundColor(.gray50)
                            .padding()
                            .background(.white.opacity(0.9))
                            .cornerRadius(10)
                    }
                    
                    Button(action: {
                        if areAllFieldsFilled() {
                            viewModel.registerUser(phoneNumber: txtPhoneNumber, name: txtName, surname: txtSurname, email: txtEmail, password: txtPassword, securityKey: txtSecurityKey)
                            isSignUpSuccessful = true  // Sign-up başarılıysa state'i güncelle
                        } else {
                            print("Please fill in all fields.")
                        }
                    }) {
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
                    
                    // Başarı durumunda SignInView'a yönlendirme
                    NavigationLink(
                        destination: SignInView(),
                        isActive: $isSignUpSuccessful,
                        label: { EmptyView() }
                    )
                }
                .navigationTitle("")
                .navigationBarBackButtonHidden(true)
                .navigationBarHidden(true)
                .onAppear(perform: addAnimation)
            }
        }
    }
    
    func areAllFieldsFilled() -> Bool {
        return !txtName.isEmpty &&
        !txtSurname.isEmpty &&
        !txtEmail.isEmpty &&
        !txtPhoneNumber.isEmpty &&
        !txtPassword.isEmpty &&
        !txtSecurityKey.isEmpty &&
        viewModel.selectedPackage != nil
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
