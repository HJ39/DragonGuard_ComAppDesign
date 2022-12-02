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
    
    func need_Info_List(choiceButton: String) -> [JejuInfo]{
        var needInfo = [JejuInfo]() //새로운 관광지 정보 담을 리스트 선언
        
        infoList = (ad?.parsing.getItemInfo())!    //api 데이터 값 가져옴
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
