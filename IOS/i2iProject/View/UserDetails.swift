import SwiftUI

struct UserDetails: View {
    let remainingInternet: CGFloat = 0.3 // 70% remaining
    let remainingMessage: CGFloat = 0.8 // 70% remaining
    let remainingCalls: CGFloat = 0.6 // 70% remaining
    let phoneNumber: String = "+90 532 567 8902"
    let packageName: String = "Premium Package"
    let validityDate: String = "Valid until: 31.12.2024"

    var body: some View {
        NavigationView {
            ZStack {
                Color.gray80.edgesIgnoringSafeArea(.all)
                
                ScrollView {
                    VStack(spacing: 10) {
                        // Telefon numarası, paket adı ve geçerlilik tarihi
                        VStack(spacing: 5) {
                            Text("Hoşgeldin Engin")
                                .font(.title)
                                .foregroundColor(.white)
                                .fontWeight(.bold)
                            
                            Text(phoneNumber)
                                .font(.headline)
                                .foregroundColor(.gray40)
                            
                            Text(packageName)
                                .font(.headline)
                                .foregroundColor(.gray40)
                            
                            Text(validityDate)
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
                        buildRemainingCard(title: "Internet", remainingText: "2.8 GB", remainingData: remainingInternet)
                        
                        // İkinci bilgi kartı (Message)
                        buildRemainingCard(title: "Message", remainingText: "1000 SMS", remainingData: remainingMessage)
                        
                        // Üçüncü bilgi kartı (Calls)
                        buildRemainingCard(title: "Calls", remainingText: "500 Minutes", remainingData: remainingCalls)
                    }
                    .padding(.top, 20)
                }
                
                // Buton - ScrollView'ın üstünde, ekranın alt kısmında yerleştirilmiş
                VStack {
                    Spacer()
                    HStack {
//                        Button {
//                            EmptyView()
//                        } label: {
//                            Text("Change Password")
//                                .font(.headline)
//                                .foregroundColor(.white)
//                                .fontWeight(.bold)
//                                .frame(width: 140, height: 55)
//                                .background(Color.grayD)
//                                .clipShape(Capsule())
//                                .cornerRadius(14)
//                        }
//                        .padding()
                        
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
        }
    }

    func buildRemainingCard(title: String, remainingText: String, remainingData: CGFloat) -> some View {
        ZStack {
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
                    
                    ArcShape(progress: remainingData)
                        .stroke(Color.grayD, lineWidth: 15)
                        .frame(width: 120, height: 120)
                    
                    Text("\(Int(remainingData * 100))%")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                }
                
                VStack(alignment: .leading, spacing: 5) {
                    Text(title)
                        .font(.title2)
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
}


#Preview {
    UserDetails()
}
