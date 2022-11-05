//
//  TestCode.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import Foundation

class testClass{
    //tableview에 사용할 테스트 리스트
    var testlist = [("title1","인천"), ("title2","서울"), ("title3","부산"), ("title4","대구"), ("title5","ㅋ"), ("title6","ㅌ"),
                    ("title7","ㅍ"),("title8","부산"),("title9","부산"),("title10","부산"),("title11","부산"),("title12","부산"),
                    ("title13","부산"),("title14","부산"),("title15","부산"),("title16","부산"),("title17","부산"),("title18","부산"),("title19","부산"),("title20","부산")]
    
    // JejuTourInFo 클래스에 저장한 후 datalist 리턴
    func testReturnList() -> [JejuTourInFo] {
        var datalist = [JejuTourInFo]() //InFo클래스 리스트 생성
        
        // testlist 정보를 JejuTourInFo 클래스배열에 저장
        for (title, address) in self.testlist{
            let info = JejuTourInFo()
            info.title = title
            info.address = address
            datalist.append(info)
        }
        return datalist
    }
    
    
    
    
}
