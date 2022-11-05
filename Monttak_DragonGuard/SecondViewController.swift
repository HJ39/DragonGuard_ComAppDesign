//
//  SecondViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Second화면 ViewController
class SecondViewController: UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.title = "Second"    //네비게이션 타이틀 지정
        self.navigationItem.rightBarButtonItem = nil    //baritem 삭제
        
    }
    
    
    //버튼 클릭 시 세 번째 화면으로 넘어가는 메소드
    @IBAction func Click_Go_ThirdScreen(_ sender: Any) {
        let third = UIStoryboard(name: "Third", bundle: nil)
        
        // UIViewController에서 ThirdViewController로 다운 캐스팅
        let thirdScreen = third.instantiateViewController(withIdentifier: "ThirdScreen") as! ThirdViewController
        
        self.navigationController?.pushViewController(thirdScreen, animated: true) //네비게이션 Controller 전용 넘어가는 구문
    }
    
}
