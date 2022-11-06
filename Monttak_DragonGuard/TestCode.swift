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
                    ("title7","ㅍ"),("title8","부산"),("title9","부산"),("title10","부산")]
    
    var testMapList = [(1.0,1.9),(2.1,2.3),(3.1,3.4),(4.2,4.3),(5.1,5.2),(6.1,6.2),(7.2,7.3),(8.3,8.6),(9.6,9.5),(10.1,10.3)]
    
    // JejuTourInFo 클래스에 저장한 후 datalist 리턴
    func testReturnList() -> [JejuTourInFo] {
        var datalist = [JejuTourInFo]() //InFo클래스 리스트 생성
        var index = 0
        
        // testlist 정보를 JejuTourInFo 클래스배열에 저장
        for (title, address) in self.testlist{
            let info = JejuTourInFo()
            info.title = title
            info.address = address
            info.latitude = testMapList[index].0
            info.longitude = testMapList[index].1
            index += 1
            datalist.append(info)
        }
        
        return datalist
    }
    
    
    
    
}
