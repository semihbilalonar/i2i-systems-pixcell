import Combine
import SwiftUI

class SignUpViewModel: ObservableObject {
    @Published var packages: [Package] = [
        Package(packageId: 1, packageName: "Basic Plan", amountMinutes: 500, price: 300.00, amountData: 5000, amountSms: 500, period: 30),
        Package(packageId: 2, packageName: "Standart Plan", amountMinutes: 750, price: 500.00, amountData: 7500, amountSms: 750, period: 60),
        Package(packageId: 3, packageName: "Premium Plan", amountMinutes: 1000, price: 800.00, amountData: 10000, amountSms: 1000, period: 90)
    ]
    
    @Published var selectedPackage: Package?
    @Published var selectedOption: String = ""
    
    private var cancellables = Set<AnyCancellable>()
    
    private var userDetailsModel = UserDetailsModel()
    
    func registerUser(phoneNumber: String, name: String, surname: String, email: String, password: String, securityKey: String) {
        guard let selectedPackage = selectedPackage else {
            print("No package selected")
            return
        }
        
        print("Selected package ID: \(selectedPackage.packageId)")
        
        let url = URL(string: "http://34.172.128.173/api/auth/registerWithPackage")!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let parameters: [String: Any] = [
            "MSISDN": phoneNumber,
            "NAME": name,
            "SURNAME": surname,
            "EMAIL": email,
            "PASSWORD": password,
            "SECURITY_KEY": securityKey,
            "PACKAGE_ID": selectedPackage.packageId
        ]
        
        // Debugging: Print parameters before sending
        print("Sending parameters: \(parameters)")
        
        // Convert parameters to JSON data
        guard let httpBody = try? JSONSerialization.data(withJSONObject: parameters, options: []) else {
            print("Failed to serialize parameters")
            return
        }
        request.httpBody = httpBody
        
        URLSession.shared.dataTaskPublisher(for: request)
            .map(\.data)
            .sink(receiveCompletion: { completionResult in
                switch completionResult {
                case .finished:
                    print("Registration request finished successfully.")
                case .failure(let error):
                    print("Registration failed with error: \(error)")
                }
            }, receiveValue: { data in
                // Debugging: Print the raw response
                let jsonString = String(data: data, encoding: .utf8) ?? "No readable data"
                print("API Response: \(jsonString)")
                
                do {
                    let decoder = JSONDecoder()
                    let response = try decoder.decode(Response.self, from: data)
                    print("Registration response: \(response)")
                } catch {
                    print("Decoding error: \(error.localizedDescription)")
                }
            })
            .store(in: &cancellables)
    }
}

struct Response: Codable {
    let success: Bool
    let message: String
}
