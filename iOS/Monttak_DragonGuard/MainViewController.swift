//
//  ViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

// Main화면 ViewController
class MainViewController: UIViewController {
    @IBOutlet var textview: UITextView!
    @IBOutlet var texttitle: UILabel!
    @IBOutlet var stackview: UIStackView!
    let screenWidth = UIScreen.main.bounds.size.width // 뷰 전체 폭 길이
    let screenHeight = UIScreen.main.bounds.size.height // 뷰 전체 높이 길이
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.backButtonTitle = " "    //SecondScreen 뒤로가기 버튼 텍스트 없앰
        
        //백그라운드를 이미지로 설정
        guard let img = UIImage(named: "secondbackground") else{ return }
        //이미지크기를 조절해서 백그라운드에 적용
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight) )
        
        //백그라운드 텍스트 타이틀 설정(제목)
        guard let img2 = UIImage(named: "texttitle") else{ return }
        //백그라운드에 적용
        texttitle.backgroundColor = UIColor(patternImage: img2)
        
        //백그라운드를 이미지로 설정
        guard let img1 = UIImage(named: "textviewimg") else{ return }
        //백그라운드에 적용
        stackview.backgroundColor = UIColor(patternImage: img1)
    }

    
    @IBAction func Click_Go_SecondScreen(_ sender: UIButton) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    
}

