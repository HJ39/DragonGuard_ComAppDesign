import Foundation

struct Result: Codable {
    let result, resultMessage: String
    let totalCount, resultCount, pageSize, pageCount: Int
    let currentPage: Int
    let items: [Item]
    
    static let sample = Result(result: "", resultMessage: "", totalCount: 0, resultCount: 0, pageSize: 0, pageCount: 0, currentPage: 0, items: [])
}

struct Item: Codable {
    let alltag: String?
    let contentsid: String
    let contentscd: Contentscd
    let title: String
    let region1CD, region2CD: Contentscd?
    let address, roadaddress, tag, introduction: String?
    let latitude, longitude: Double?
    let postcode, phoneno: String?
    let repPhoto: RepPhoto?

    enum CodingKeys: String, CodingKey {
        case alltag, contentsid, contentscd, title
        case region1CD = "region1cd"
        case region2CD = "region2cd"
        case address, roadaddress, tag, introduction, latitude, longitude, postcode, phoneno, repPhoto
    }
    
    static let ItemSample = Item(alltag: "", contentsid: "", contentscd: Contentscd(value: "", label: "", refID: ""), title: "", region1CD: Contentscd(value: "", label: "", refID: ""), region2CD: Contentscd(value: "", label: "", refID: ""), address: "", roadaddress: "", tag: "", introduction: "", latitude: 0, longitude: 0, postcode: "", phoneno: "", repPhoto: RepPhoto(descseo: "", photoid: Photoid(photoid: 0, imgpath: "", thumbnailpath: "")))
    
}

struct Contentscd: Codable {
    let value, label, refID: String

    enum CodingKeys: String, CodingKey {
        case value, label
        case refID = "refId"
    }
}

struct RepPhoto: Codable {
    let descseo: String
    let photoid: Photoid
}

struct Photoid: Codable {
    let photoid: Int
    let imgpath, thumbnailpath: String
}
