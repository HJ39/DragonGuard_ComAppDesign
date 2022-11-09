//
//  JejuInfoList.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/09.
//

import Foundation
import UIKit

class JejuInfoList{
    let ad = UIApplication.shared.delegate as? AppDelegate  // 델리게이트 파일에 접근 할 수 있는 코드
    
    func return_Info_List() -> [JejuInfo] {
        let datalist = ad?.parsing.getItemInfo()    //api 데이터 값 가져옴
        var infoList = [JejuInfo]() //새로운 관광지 정보 담을 리스트 선언
        
        // 리스트에 필요한 정보만 담음
        for index in 1..<(datalist?.count ?? 1){
            let info = JejuInfo()
            info.title = datalist?[index].title
            info.address = datalist?[index].roadaddress
            info.imgURL = datalist?[index].repPhoto?.photoid.imgpath
            info.introduction = datalist?[index].introduction
            info.latitude = datalist?[index].latitude
            info.longitude = datalist?[index].longitude
            infoList.append(info)
        }
        return infoList
    }
}
