//
//  ViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

// Main화면 ViewController
class MainViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.backButtonTitle = " "    //SecondScreen 뒤로가기 버튼 텍스트 없앰
        
        // Do any additional setup after loading the view.
    }

    
    @IBAction func Click_Go_SecondScreen(_ sender: UIButton) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        secondScreen.choiceButton = " " 
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    
}

