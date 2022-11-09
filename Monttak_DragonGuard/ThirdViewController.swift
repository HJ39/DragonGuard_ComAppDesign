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
    
    var tourPlaceName: String?  //관광지 이름
    var tourPlaceIndex: Int?    //관광지가 해당하는 배열의 인덱스
    var testList = testClass().testReturnList() //테스트를 위한 코드
    @IBOutlet var imgView: UIImageView!
    
    // 뷰 전체 폭 길이
    let screenWidth = UIScreen.main.bounds.size.width
    // 뷰 전체 높이 길이
    let screenHeight = UIScreen.main.bounds.size.height
    
    @IBAction func click_Go_Map(_ sender: Any) {
        NSLog("map button clicked")
    }
    
    @IBAction func click_Go_Call(_ sender: Any) {
        NSLog("call button clicked")
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let img = UIImage(named: "배경5")else{ return }
        
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight))
        self.navigationItem.title = tourPlaceName ?? "Jeju Tour"
        
        imgView.image = UIImage(named:"삼겹살")?.resize(newWidth: screenWidth)
        imgView.layer.cornerRadius = 40
        
    }
}


