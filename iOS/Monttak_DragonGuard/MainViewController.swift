//
//  ViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

// Main화면 ViewController
class MainViewController: UIViewController {
    let screenWidth = UIScreen.main.bounds.size.width // 뷰 전체 폭 길이
    let screenHeight = UIScreen.main.bounds.size.height // 뷰 전체 높이 길이
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.backButtonTitle = " "    //SecondScreen 뒤로가기 버튼 텍스트 없앰
        
        //백그라운드를 이미지로 설정
        guard let img = UIImage(named: "배경2") else{ return }
        //이미지크기를 조절해서 백그라운드에 적용
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight) )
        
        
    }

    
    @IBAction func Click_Go_SecondScreen(_ sender: UIButton) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        secondScreen.choiceButton = "볼거리"   //사용자가 누르는 버튼 이름을 넣어야함
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    
}

