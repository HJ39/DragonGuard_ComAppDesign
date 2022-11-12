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
    @IBOutlet var tourAddress: UILabel!
    @IBOutlet var imgview: UIImageView!
    @IBOutlet var introduction: UITextView!
    @IBOutlet var phoneNumber: UILabel!
    
    var tourPlaceIndex: Int = 0    //관광지가 해당하는 배열의 인덱스
    
    // 뷰 전체 폭 길이
    let screenWidth = UIScreen.main.bounds.size.width
    // 뷰 전체 높이 길이
    let screenHeight = UIScreen.main.bounds.size.height
    //필요한 정보만 있는 제주 데이터 리스트
    var datalist = JejuInfoList().return_Info_List()
    
    //누르면 지도 앱 실행
    @IBAction func click_Go_Map(_ sender: Any) {
        NSLog("map button clicked")
    }
    
    // 누르면 전화번호 전화 앱에 전화번호 입력된다.
    @IBAction func click_Go_Call(_ sender: Any) {
        NSLog("call button clicked")
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let img = UIImage(named: "배경5")else{ return }
        let attributedString1 = NSMutableAttributedString(string: "")
        let attributedString2 = NSMutableAttributedString(string: "")
        
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight))
        self.navigationItem.title = datalist[tourPlaceIndex].title ?? ""
        self.introduction.text = datalist[tourPlaceIndex].introduction
        
        // 주소 label 앞에 핀 아이콘 넣어주는 코드
        let imageAttachment1 = NSTextAttachment()
        imageAttachment1.image = UIImage(systemName: "mappin.and.ellipse")
        attributedString1.append(NSAttributedString(attachment: imageAttachment1))
        attributedString1.append(NSAttributedString(string: datalist[tourPlaceIndex].address ?? "정보 없음"))
        self.tourAddress.attributedText = attributedString1
        
        // 전화번호 label 앞에 전화 아이콘 넣는 코드
        let imageAttachment2 = NSTextAttachment()
        imageAttachment2.image = UIImage(systemName: "phone")
        attributedString2.append(NSAttributedString(attachment: imageAttachment2))
        attributedString2.append(NSAttributedString(string: datalist[tourPlaceIndex].phoneNumber ?? "정보 없음"))
        self.phoneNumber.attributedText = attributedString2
        
        // 이미지 넣어 주면 됨
        imgview.image = UIImage(named:"삼겹살")?.resize(newWidth: screenWidth)
        imgview.layer.cornerRadius = 40
        
    }
}


