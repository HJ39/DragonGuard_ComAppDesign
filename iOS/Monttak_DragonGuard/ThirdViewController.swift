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
        //위도 경도 변수
        let latitude = datalist[tourPlaceIndex].latitude ?? 0
        let longitude = datalist[tourPlaceIndex].longitude ?? 0
        
        let url = URL(string: "kakaomap://look?p=\(latitude),\(longitude)") //URL 지정
        if UIApplication.shared.canOpenURL(URL(string: "kakaomap://")!){    //카카오맵 scheme 탐색 후 있는 경우 실행
            UIApplication.shared.open(url!, options: [:],completionHandler: nil)
        }
        else{
            // 카카오맵이 없는 경우 앱스토어로 이동
            if let openStore = URL(string: "itms-apps://itunes.apple.com/app/id304608425"), UIApplication.shared.canOpenURL(openStore) {
                UIApplication.shared.open(openStore, options: [:], completionHandler: nil)
            }
        }
        
    }
    
    // 누르면 해당 전화번호에 전화를 걸건지 물어본다.
    @IBAction func click_Go_Call(_ sender: Any) {
        NSLog("call button clicked")
        let phoneNumber = datalist[tourPlaceIndex].phoneNumber ?? "000-0000-0000"
        print(phoneNumber.components(separatedBy: ["-"]).joined())
        guard let integerNumber = Int(phoneNumber.components(separatedBy: ["-"]).joined()) else{ return }

        let url = URL(string: "tel://\(integerNumber)") //URL 지정
        if UIApplication.shared.canOpenURL(URL(string: "tel://")!){    //전화 앱scheme 탐색 후 있는 경우 실행
            UIApplication.shared.open(url!, options: [:],completionHandler: nil)
        }
        else{
            print("can't open tel")
        }
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let img = UIImage(named: "배경2")else{ return }
        let attributedString1 = NSMutableAttributedString(string: "")
        let attributedString2 = NSMutableAttributedString(string: "")
        
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight))
        self.navigationItem.title = datalist[tourPlaceIndex].title ?? ""
        self.introduction.text = datalist[tourPlaceIndex].introduction
        
        // url 이미지 주소 설정하는 코드
        let url = URL(string: datalist[tourPlaceIndex].imgURL ?? "")!
        imgview?.load(img: imgview!,url: url,screenWidth: screenWidth)  //휴대폰 기기의 가로, 세로 길이를 넘겨서 비율에 맞게 이미지 표시
        imgview.layer.cornerRadius = 40
        
        // 주소 label 앞에 핀 아이콘 넣어주는 코드
        let imageAttachment1 = NSTextAttachment()
        imageAttachment1.image = UIImage(systemName: "mappin.and.ellipse")
        attributedString1.append(NSAttributedString(attachment: imageAttachment1))
        attributedString1.append(NSAttributedString(string: datalist[tourPlaceIndex].address ?? "정보 없음"))
        self.tourAddress.attributedText = attributedString1
        
        // 전화번호 label 앞에 전화 아이콘 넣는 코드
        let imageAttachment2 = NSTextAttachment()
        imageAttachment2.image = UIImage(systemName: "phone")
        imageAttachment2.image?.withTintColor(UIColor(red: 100/255.0, green: 100/255.0, blue: 100/255.0, alpha: 0.5))
        attributedString2.append(NSAttributedString(attachment: imageAttachment2))
        attributedString2.append(NSAttributedString(string: datalist[tourPlaceIndex].phoneNumber ?? "정보 없음"))
        self.phoneNumber.attributedText = attributedString2
        
        
        
    }
}


