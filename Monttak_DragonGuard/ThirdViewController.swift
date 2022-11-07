//
//  ThirdViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Third 화면 클래스
class ThirdViewController: UIViewController{
    
    @IBOutlet var scrollView: UIScrollView!
    @IBOutlet var imgView: UIImageView!
    @IBOutlet var mapButton: UIButton!
    @IBOutlet var callButton: UIButton!
    
    var tourPlaceName: String?  //관광지 이름
    var tourPlaceIndex: Int?    //관광지가 해당하는 배열의 인덱스
    var testList = testClass().testReturnList() //테스트를 위한 코드
    
    @IBAction func click_Go_Map(_ sender: Any) {
        NSLog("map button clicked")
    }
    
    @IBAction func click_Go_Call(_ sender: Any) {
        NSLog("call button clicked")
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = tourPlaceName ?? "Jeju Tour"
        
//        imgView.image = UIImage(named: "짜장면")
        
//        let img = cell.viewWithTag(100) as? UIImageView // 이미지를 표시할 변수
//        img?.image = UIImage(named:"삼겹살")
        
        
        
        
        
    }
}
