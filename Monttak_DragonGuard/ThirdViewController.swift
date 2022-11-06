//
//  ThirdViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Third 화면 클래스
class ThirdViewController: UIViewController{
    
    var tourPlaceName: String?  //관광지 이름
    var tourPlaceIndex: Int?    //관광지가 해당하는 배열의 인덱스
    var testList = testClass().testReturnList() //테스트를 위한 코드
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = tourPlaceName ?? "Jeju Tour"
        
//        let img = cell.viewWithTag(100) as? UIImageView // 이미지를 표시할 변수
//        img?.image = UIImage(named:"삼겹살")
        
        
        
    }
}
