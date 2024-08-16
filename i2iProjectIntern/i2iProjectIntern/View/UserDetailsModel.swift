import Foundation

class UserDetailsModel: ObservableObject {
    @Published var balanceData: Double = 0
    @Published var balanceSms: Double = 0
    @Published var balanceMinutes: Double = 0
    
    @Published var name: String = ""
    
    @Published var packageId: Int = 0
    @Published var packageName: String = ""
    @Published var amountMinutes: Double = 0.0
    @Published var price: Double = 0.0
    @Published var amountData: Double = 0.0
    @Published var amountSms: Double = 0.0
    @Published var period: Double = 0.0
    
//    @Published var packageData: Int = 0
//    @Published var packageSms: Int = 0
//    @Published var packageMinutes: Int = 0
    
    func fetchBalanceData(msisdn: String) {
        guard let url = URL(string: "http://34.172.128.173/api/balance/getRemainingCustomerBalanceByMsisdn/\(msisdn)") else {
            print("Invalid URL")
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                print("Network Error: \(error?.localizedDescription ?? "Unknown error")")
                return
            }
            
            if let jsonString = String(data: data, encoding: .utf8) {
//                print("JSON Response: \(jsonString)")
            }
            
            do {
                let balance = try JSONDecoder().decode(RemainingBalance.self, from: data)
                DispatchQueue.main.async {
                    self.balanceData = Double(balance.balanceData)
                    self.balanceSms = Double(balance.balanceSms)
                    self.balanceMinutes = Double(balance.balanceMinutes)
//                    print("Balance Data \(self.balanceData)")
                }
            } catch {
                print("Failed to decode JSON: \(error)")
            }
        }.resume()
    }
    
    func fetchCustomerData(msisdn: String) {
        guard let url = URL(string: "http://34.172.128.173/api/customer/getCustomerByMsisdn/\(msisdn)") else {
            print("Invalid URL")
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                print("Network Error: \(error?.localizedDescription ?? "Unknown error")")
                return
            }
            
            if let jsonString = String(data: data, encoding: .utf8) {
                print("JSON Response: \(jsonString)")
            }
            
            do {
                let customer = try JSONDecoder().decode([Customer].self, from: data)
                if let firsCustomer = customer.first {
                    DispatchQueue.main.async {
                        self.name = firsCustomer.name ?? "Unknown User"
                        print("Customer Name \(self.name)")
                    }
                } else {
                    print("No customer found in the response.")
                }
            } catch let decodingError as DecodingError {
                print("Decoding error: \(decodingError)")
            } catch {
                print("Unexpected error: \(error)")
            }
        }.resume()
    }
    
    func fetchPackageData(msisdn: String) {
        guard let url = URL(string: "http://34.172.128.173/api/package/getPackageDetailsByMsisdn/\(msisdn)") else {
            print("Invalid URL")
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {
                print("Network Error: \(error?.localizedDescription ?? "Unknown error")")
                return
            }
            
            if let jsonString = String(data: data, encoding: .utf8) {
                print("JSON Response: \(jsonString)")
            }
            
            do {
                let packages = try JSONDecoder().decode([Package].self, from: data)
                
                if let package = packages.first {
                    DispatchQueue.main.async {
                        self.packageId = package.packageId
                        self.packageName = package.packageName
                        self.amountMinutes = Double(package.amountMinutes)
                        self.price = Double(package.price)
                        self.amountData = Double(package.amountData)
                        self.amountSms = Double(package.amountSms)
                        self.period = Double(package.period)
                        print("Package Name: \(self.packageName)")
                    }
                } else {
                    print("No package found in the response.")
                }
            } catch {
                print("Failed to decode JSON: \(error)")
            }
        }.resume()
    }
    
//    func fetchPackageData(msisdn: String) {
//            guard let url = URL(string: "http://34.172.128.173/api/package/getPackageById/\(msisdn)") else {
//                print("Invalid URL")
//                return
//            }
//            
//            var request = URLRequest(url: url)
//            request.httpMethod = "GET"
//            
//            URLSession.shared.dataTask(with: request) { data, response, error in
//                guard let data = data, error == nil else {
//                    print("Network Error: \(error?.localizedDescription ?? "Unknown error")")
//                    return
//                }
//                
//                if let jsonString = String(data: data, encoding: .utf8) {
//                    print("JSON Response: \(jsonString)")
//                }
//                
//                do {
//                    let package = try JSONDecoder().decode(Package.self, from: data)
//                    DispatchQueue.main.async {
//                        self.packageData = Int(package.amountData)
//                        self.packageSms = Int(package.amountSms)
//                        self.packageMinutes = Int(package.amountMinutes)
//                    }
//                } catch {
//                    print("Failed to decode JSON: \(error)")
//                }
//            }.resume()
//        }
}
