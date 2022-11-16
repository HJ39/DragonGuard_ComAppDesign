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
    var infoList = [JejuInfo]() //새로운 관광지 정보 담을 리스트 선언
    
    init(){
        let datalist = ad?.parsing.getItemInfo()    //api 데이터 값 가져옴
        // 리스트에 필요한 정보만 담음
        for index in 1..<(datalist?.count ?? 1){
            let info = JejuInfo()
            info.title = datalist?[index].title
            info.address = datalist?[index].roadaddress
            info.imgURL = datalist?[index].repPhoto?.photoid.imgpath
            info.introduction = datalist?[index].introduction
            info.latitude = datalist?[index].latitude
            info.longitude = datalist?[index].longitude
            info.phoneNumber = datalist?[index].phoneno
            info.type =  datalist?[index].contentscd.label
            infoList.append(info)
        }
    }
    // 버튼에 맞는 정보만 리턴하는 함수
    func need_Info_List(choiceButton: String) -> [JejuInfo]{
        var needInfo = [JejuInfo]() //새로운 관광지 정보 담을 리스트 선언
        
        for index in 0..<(infoList.count){
            
            if (choiceButton == "먹거리" && infoList[index].type == "음식점"){
                needInfo.append(infoList[index])
            }
            else if (choiceButton == "놀멍" && (infoList[index].type == "테마여행" || infoList[index].type == "쇼핑" )){
                needInfo.append(infoList[index])
            }
            else if (choiceButton == "볼거리" && (infoList[index].type == "정보" || infoList[index].type == "축제/행사" )){
                needInfo.append(infoList[index])
            }
            else if (choiceButton == "쉴멍" && infoList[index].type == "숙박"){
                needInfo.append(infoList[index])
            }
        }
        return needInfo
    }
    
    
    
}
