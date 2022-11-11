//
//  Parsing.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/09.
//

import Foundation

class Parsing {
    var decodedItem: [Item] = [Item.ItemSample]
    let apiKey = "rrq71a2rotyj9tqm"
    let STARTPAGE = 44
    let ENDPAGE = 45
    
    func getData(){
        
        for i in STARTPAGE...ENDPAGE{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global(qos: .background).async {
                
                guard let url = URL(string: urlString) else {print("url 찾을 수 없음"); return}
                var request = URLRequest.init(url: url)
                request.httpMethod = "GET"
                
                let daskTask = URLSession.shared.dataTask(with: request){ data, res, err in
                    if let err = err{ print("Error!",err); return }
                    guard data != nil else { print("data 없음"); return }
                    guard let res = res as? HTTPURLResponse else { return }
                    
                    if res.statusCode == 200 {
                        guard let data = data else { return }
                        DispatchQueue.global().async {
                            do {
                                let startTime = CFAbsoluteTimeGetCurrent()
                                
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                print(decodedData.currentPage)
                                
                                let durationTime = CFAbsoluteTimeGetCurrent() - startTime
                                print("경과 시간: \(durationTime)")
                                
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }   //thread
                        
                    } // if문
                }//daskTask
                daskTask.resume()
            }
        }
        
    }//getData
    
    func getItemInfo() -> [Item]{       //MainScreen으로 파싱된 데이터를 전달하는 함수
        return self.decodedItem
    }
}
