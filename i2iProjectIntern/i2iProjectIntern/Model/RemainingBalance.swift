//
//  RemainingBalance.swift
//  i2iProjectIntern
//
//  Created by Engin Gündüz on 14.08.2024.
//

import Foundation

struct RemainingBalance: Codable {
    let balanceData: Int
    let balanceSms: Int
    let balanceMinutes: Int
    let sdate: String?
    let edate: String?
}
