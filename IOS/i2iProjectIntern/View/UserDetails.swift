import SwiftUI


struct UserDetails: View {
    @Environment(\.presentationMode) var presentationMode
    @StateObject private var viewModel = UserDetailsModel()
    @State private var timer: Timer?
    let phoneNumber: String
    let packageName: String = ""
    let validityDate: String = ""
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.gray80.edgesIgnoringSafeArea(.all)
                
                ScrollView {
                    VStack(spacing: 10) {
                        // Telefon numarası, paket adı ve geçerlilik tarihi
                        VStack(spacing: 5) {
                            Text("Hoşgeldin \(viewModel.name)")
                                .font(.title)
                                .foregroundColor(.white)
                                .fontWeight(.bold)
                            
                            Text(phoneNumber)
                                .font(.headline)
                                .foregroundColor(.gray40)
                            
                            Text(viewModel.packageName)
                                .font(.headline)
                                .foregroundColor(.gray40)
                            
                            Text(String(viewModel.price))
                                .font(.headline)
                                .foregroundColor(.gray40)
                        }
                        .padding(.top, 60)
                        
                        // Remaining title
                        Text("Remainings")
                            .font(.title2)
                            .fontWeight(.bold)
                            .foregroundColor(.white)

                        // İlk bilgi kartı (Internet)
                        buildRemainingCard(title: "Internet", remainingText: "\(viewModel.amountData) MB", remainingData: CGFloat(viewModel.balanceData), packageData: CGFloat(viewModel.amountData))
                        
                        // İkinci bilgi kartı (Message)
                        buildRemainingCard(title: "Message", remainingText: "\(viewModel.amountSms) SMS", remainingData: CGFloat(viewModel.balanceSms), packageData: CGFloat(viewModel.amountSms))
                        
                        // Üçüncü bilgi kartı (Calls)
                        buildRemainingCard(title: "Calls", remainingText: "\(viewModel.amountMinutes) Minutes", remainingData: CGFloat(viewModel.balanceMinutes), packageData: CGFloat(viewModel.amountMinutes))
                    }
                    .padding(.top, 20)
                }
                
                // Buton - ScrollView'ın üstünde, ekranın alt kısmında yerleştirilmiş
                VStack {
                    Spacer()
                    HStack {
                        NavigationLink {
                            SignInView()
                                .navigationBarBackButtonHidden(true)
                        } label: {
                            Text("Exit")
                                .font(.headline)
                                .foregroundColor(Color.grayD)
                                .fontWeight(.bold)
                                .frame(width: 180, height: 55)
                                .background(Color.white)
                                .clipShape(Capsule())
                                .cornerRadius(14)
                        }
                        .padding()
                    }
                }
            }
            .ignoresSafeArea()
            .onAppear {
                viewModel.fetchBalanceData(msisdn: phoneNumber)
                viewModel.fetchCustomerData(msisdn: phoneNumber)
                viewModel.fetchPackageData(msisdn: phoneNumber)
                startTimer()
            }
            .onDisappear {
                timer?.invalidate()
            }
        }
    }
    
    func buildRemainingCard(title: String, remainingText: String, remainingData: CGFloat, packageData: CGFloat) -> some View {
        let progress = remainingData / packageData
        
        return ZStack {
            Rectangle()
                .foregroundColor(.gray80)
                .frame(width: .screenWidth - 20, height: .widthPer(per: 0.4))
                .cornerRadius(27)
            
            HStack(spacing: 30) {
                Spacer()
                ZStack {
                    Circle()
                        .trim(from: 0, to: 1)
                        .stroke(Color.gray10.opacity(0.2), lineWidth: 15)
                        .frame(width: 120, height: 120)
                    
                    ArcShape(progress: progress)
                        .stroke(Color.grayD, lineWidth: 15)
                        .frame(width: 120, height: 120)
                    
                    Text("\(Int(remainingData))")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                }
                
                VStack(alignment: .leading, spacing: 5) {
                    Text(title)
                        .font(.title)
                        .fontWeight(.semibold)
                        .foregroundColor(Color.grayD)
                        .frame(maxWidth: .infinity, alignment: .trailing)
                    
                    Text(remainingText)
                        .font(.title2)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity, alignment: .trailing)
                }
                .padding(.trailing, 25)
            }
            .frame(maxWidth: .infinity)
        }
    }
    
    func startTimer() {
            timer = Timer.scheduledTimer(withTimeInterval: 1, repeats: true) { _ in
                viewModel.fetchBalanceData(msisdn: phoneNumber)
            }
        }
    
    func signOut() {
        // Kullanıcı bilgilerini sıfırla
        UserDefaults.standard.removeObject(forKey: "authToken")
        UserDefaults.standard.removeObject(forKey: "userPhoneNumber")
        UserDefaults.standard.removeObject(forKey: "userPassword")
        
        // Oturumu sonlandırma ve giriş ekranına yönlendirme
        presentationMode.wrappedValue.dismiss()
    }
}


#Preview {
    UserDetails(phoneNumber: "")
}
