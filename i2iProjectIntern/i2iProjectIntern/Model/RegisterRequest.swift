import Foundation


struct RegisterRequest: Codable {
    let msisdn: String
    let name: String
    let surname: String
    let email: String
    let password: String
    let securityKey: String
    let package: Package
}
