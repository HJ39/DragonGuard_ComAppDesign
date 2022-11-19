//
//  ThirdViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Third 화면 클래스
class ThirdViewController: UIViewController{
    
    @IBOutlet var tourAddress: UILabel!
    @IBOutlet var imgview: UIImageView!
    @IBOutlet var introduction: UITextView!
    @IBOutlet var phoneNumber: UILabel!
    @IBOutlet var collectionView: UICollectionView!
    
    var tourPlaceIndex: Int = 0    //관광지가 해당하는 배열의 인덱스
    
    // 뷰 전체 폭 길이
    let screenWidth = UIScreen.main.bounds.size.width
    // 뷰 전체 높이 길이
    let screenHeight = UIScreen.main.bounds.size.height
    //필요한 정보만 있는 제주 데이터 리스트
    var datalist: [JejuInfo]?
    var nowpage = 0 //광고 현재 페이지 위치
    var adArray: [String] = ["광고1","광고2","광고3","광고4"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let img = UIImage(named: "thirdbackground")else{ return }
        let attributedString1 = NSMutableAttributedString(string: "")
        let attributedString2 = NSMutableAttributedString(string: "")
        
        //네비게이션 타이틀 설정하는 코드
        let titleName = UILabel()
        titleName.font = UIFont(name: "OTMogujasusimgyeolB" , size: 25) //목우자심결 폰트 적용
        titleName.text = datalist?[tourPlaceIndex].title ?? ""
        self.navigationItem.titleView = titleName    //네비게이션 타이틀 지정
        
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight))
        self.introduction.text = datalist?[tourPlaceIndex].introduction
        
        // url 이미지 주소 설정하는 코드
        let url = URL(string: datalist?[tourPlaceIndex].imgURL ?? "")!
        imgview?.load(img: imgview!,url: url,screenWidth: screenWidth)  //휴대폰 기기의 가로, 세로 길이를 넘겨서 비율에 맞게 이미지 표시
        imgview.layer.cornerRadius = 40
        
        // 주소 label 앞에 핀 아이콘 넣어주는 코드
        let imageAttachment1 = NSTextAttachment()
        imageAttachment1.image = UIImage(systemName: "mappin.and.ellipse")
        attributedString1.append(NSAttributedString(attachment: imageAttachment1))
        attributedString1.append(NSAttributedString(string: datalist?[tourPlaceIndex].address ?? "정보 없음"))
        self.tourAddress.attributedText = attributedString1
        
        // 전화번호 label 앞에 전화 아이콘 넣는 코드
        let imageAttachment2 = NSTextAttachment()
        imageAttachment2.image = UIImage(systemName: "phone")
        imageAttachment2.image?.withTintColor(UIColor(red: 100/255.0, green: 100/255.0, blue: 100/255.0, alpha: 0.5))
        attributedString2.append(NSAttributedString(attachment: imageAttachment2))
        attributedString2.append(NSAttributedString(string: datalist?[tourPlaceIndex].phoneNumber ?? "정보 없음"))
        self.phoneNumber.attributedText = attributedString2
        
        timer() //광고 타이머
    }
    
    //누르면 지도 앱 실행
    @IBAction func click_Go_Map(_ sender: Any) {
        //위도 경도 변수
        let latitude = datalist?[tourPlaceIndex].latitude ?? 0
        let longitude = datalist?[tourPlaceIndex].longitude ?? 0
        
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
        let phoneNumber = datalist?[tourPlaceIndex].phoneNumber ?? "000-0000-0000"
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
    
    func timer(){   //광고 타이머
        let _: Timer = Timer.scheduledTimer(withTimeInterval: 3, repeats: true) { (Timer) in
            self.move_Ad_Screen()
        }
    }
    
    func move_Ad_Screen(){  //타이머 지정 시간마다 다음으로 넘기는 함수
        if nowpage == adArray.count-1 {
            collectionView.scrollToItem(at: NSIndexPath(item: 0, section: 0) as IndexPath, at: .right, animated: true)
            nowpage = 0
            return
        }
        nowpage += 1
        collectionView.scrollToItem(at: NSIndexPath(item: nowpage, section: 0) as IndexPath, at: .right, animated: true)
    }
}


extension ThirdViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout{
    //collectionView cell 개수
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return adArray.count
    }
    
    //collectionView 내부 cell 설정
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let collectionCell = collectionView.dequeueReusableCell(withReuseIdentifier: "CustomColl", for: indexPath) as! ThirdScreenCollectionViewCell

        collectionCell.imgView.image = UIImage(named: adArray[indexPath.row])
        return collectionCell
    }
    
    // collectionView 의 cell 크기 설정
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let width: CGFloat = collectionView.frame.width //collectionview의 가로 길이
        let height: CGFloat = collectionView.frame.height   //collectionview의 세로 길이
        return CGSize(width: width, height: height)
    }
    
}
