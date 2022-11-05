//
//  ViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

// Main화면 ViewController
class MainViewController: UIViewController {

    @IBOutlet var first: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func firstbutton(_ sender: UIButton) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        
        self.navigationController?.pushViewController(secondScreen, animated: true)
//        self.present(secondScreen, animated: true)
    }
    
}

