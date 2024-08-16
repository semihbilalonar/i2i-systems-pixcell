//
//  PackageDetails.swift
//  i2iProjectIntern
//
//  Created by Engin Gündüz on 14.08.2024.
//

import Foundation

struct Package: Identifiable, Codable {
    var id: Int { packageId }
    let packageId: Int
    let packageName: String
    let amountMinutes: Double
    let price: Double
    let amountData: Double
    let amountSms: Double
    let period: Double
}
