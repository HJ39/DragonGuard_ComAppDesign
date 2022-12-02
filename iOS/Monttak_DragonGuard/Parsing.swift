//
//  Parsing.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/09.
//

import Foundation

class Parsing {
    var decodingItems = [JejuInfo]()
    var count = 0
    var ip = ""
    func getData(){

            let urlString = "http://\(ip):5001/api/1/200"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
                var request = URLRequest(url: url)
                request.httpMethod = "GET"
                request.addValue("application/json", forHTTPHeaderField: "accept")
                
                let daskTask = URLSession.shared.dataTask(with: request){ data, res, err in
                    if let err = err{ print("Error!",err); return }
                    guard data != nil else { print("data 없음"); return }
                    guard let res = res as? HTTPURLResponse else { return }
                        
                    if res.statusCode == 200 {
                        guard let data = data else { print("no data"); return }
                        DispatchQueue.global().async {
                            do {
                                let decodedData = try JSONDecoder().decode([CodableResult].self, from: data)
                                
                                for d in decodedData{
                                    let eachData = JejuInfo()
                                    eachData.introduction = d.introduction
                                    eachData.imgURL = d.thumbnailpath
                                    eachData.address = d.address
                                    eachData.phoneNumber = d.phoneno
                                    eachData.type = d.contentscdlabel
                                    eachData.title = d.title
                                    eachData.latitude = d.latitude
                                    eachData.longitude = d.longitude
                                    self.decodingItems.append(eachData)
                                    self.count += 1
                                }
                                if self.decodingItems.count == 200{
                                    print("Done")
                                }
                                
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    }
                }
                daskTask.resume()
            }
    }//getData
    
    func getItemInfo() -> [JejuInfo]{       //MainScreen으로 파싱된 데이터를 전달하는 함수
        return self.decodingItems
    }
}
