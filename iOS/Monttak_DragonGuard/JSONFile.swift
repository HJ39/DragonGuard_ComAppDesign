import Foundation

struct ResultModel : Codable{
    let items: [CodableResult]
}

struct CodableResult: Codable{
    let address, road_address : String?
    let introduction: String?
    let phoneno: String?
    let tag, alltag: String?
    let thumbnailpath, imgPath, title: String?
    let contentscdvalue,contentscdlabel,contentscdrefid : String?
    let latitude, longitude: Double?
}
