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
    let STARTPAGE = 1
    let ENDPAGE = 45
    
    var count = 0
    func getData(){
        
        // 1
        for i in STARTPAGE...5{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 6...10{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 11...15{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 16...20{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 21...25{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 26...30{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 31...35{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 36...40{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
                    } // if문
                    
                }//daskTask
                daskTask.resume()
            }
        }
        
        // 1
        for i in 41...45{
            let urlString = "https://api.visitjeju.net/vsjApi/contents/searchList?apiKey=\(apiKey)&locale=kr&page=\(i)"
            DispatchQueue.global().async {
                guard let url = URL(string: urlString) else { print("url 찾을 수 없음"); return}
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
                                let decodedData = try JSONDecoder().decode(Result.self, from: data)
                                self.decodedItem.append( contentsOf: decodedData.items)
                                
                                self.count += 1
                                if self.count == 45{
                                    print("Done")
                                }
                            } catch let error {
                                print("Error decoding: ", error)
                            }
                        }
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
