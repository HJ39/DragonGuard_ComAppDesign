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
    @IBOutlet var customView: UICollectionView!
    
    let screenWidth = UIScreen.main.bounds.size.width // 뷰 전체 폭 길이
    let screenHeight = UIScreen.main.bounds.size.height // 뷰 전체 높이 길이
    var adArray: [String] = ["광고1","광고2","광고3","광고4"]
    var nowpage = 0
    @IBOutlet var button1: UIButton!
    @IBOutlet var button2: UIButton!
    @IBOutlet var button4: UIButton!
    @IBOutlet var button3: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.backButtonTitle = " "    //SecondScreen 뒤로가기 버튼 텍스트 없앰
        
        button1.setImage(UIImage(named: "버튼1")?.resize(newWidth: 120, newHeight: 120), for: .normal)
        button2.setImage(UIImage(named: "버튼2")?.resize(newWidth: 120, newHeight: 120), for: .normal)
        button3.setImage(UIImage(named: "버튼3")?.resize(newWidth: 120, newHeight: 120), for: .normal)
        button4.setImage(UIImage(named: "버튼4")?.resize(newWidth: 120, newHeight: 120), for: .normal)
        
        texttitle.font = UIFont(name: "OTMogujasusimgyeolB" , size: 40) //목우자수심결 폰트 적용
        texttitle.text = "[몬딱,제주]"
        
<<<<<<< Updated upstream
=======
        textview.font = UIFont(name: "OTMogujasusimgyeolB" , size: 20) //목우자수심결 폰트 적용
        textview.text = "- '몬딱, 제주'는 '모두 함께, 제주'라는 뜻의 제주 방언입니다. 육지에 사는 당신이 아름다운 섬 제주도를 만끽할 수 있도록 여러가지 정보를 제공합니다.\n\n- 오른쪽의 각 버튼을 클릭하면 정보가 나옵니다. 먹거리, 놀거리, 볼거리와 쉴 곳이 가득한 제주에서 당신의 인생에 쉼표를 찍어보세요. \n\n-먹거리에는 식당과 카페, 놀멍에는 테마여행과 쇼핑, 볼거리에는 축제 및 행사 정보, 쉴멍에는 숙박업소 정보가 있습니다. \n\n- 이 앱의 정보는 제주관광공사의 오픈API를 받아서 제작했습니다. "
>>>>>>> Stashed changes
        
        //백그라운드를 이미지로 설정
        guard let img = UIImage(named: "secondbackground") else{ return }
        //이미지크기를 조절해서 백그라운드에 적용
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight) )
        
        //백그라운드 텍스트 타이틀 설정(제목)
        guard let img2 = UIImage(named: "texttitle")?.resize(newWidth: 280, newHeight: 70) else{ return }
        //백그라운드에 적용
        texttitle.backgroundColor = UIColor(patternImage: img2)
        
        //백그라운드를 이미지로 설정
        guard let img1 = UIImage(named: "textviewimg")?.resize(newWidth: 280, newHeight: screenHeight) else{ return }
        //백그라운드에 적용
        stackview.backgroundColor = UIColor(patternImage: img1)
        timer()
    }
 
    @IBAction func button1(_ sender: Any) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        secondScreen.choiceButton = "볼거리"
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    
    @IBAction func button2(_ sender: Any) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        secondScreen.choiceButton = "먹거리"
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    @IBAction func button3(_ sender: Any) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        secondScreen.choiceButton = "놀멍"
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    
    @IBAction func button4(_ sender: Any) {
        let secondStory = UIStoryboard(name: "Second", bundle: nil)
        let secondScreen = secondStory.instantiateViewController(withIdentifier: "SecondScreen") as! SecondViewController
        secondScreen.choiceButton = "쉴멍"
        self.navigationController?.pushViewController(secondScreen, animated: true)
    }
    
    func timer(){   //광고 타이머
        let _: Timer = Timer.scheduledTimer(withTimeInterval: 3, repeats: true) { (Timer) in
            self.move_Ad_Screen()
        }
    }
    
    func move_Ad_Screen(){  //타이머 지정 시간마다 다음으로 넘기는 함수
        if nowpage == adArray.count-1 {
            customView.scrollToItem(at: NSIndexPath(item: 0, section: 0) as IndexPath, at: .right, animated: true)
            nowpage = 0
            return
        }
        nowpage += 1
        customView.scrollToItem(at: NSIndexPath(item: nowpage, section: 0) as IndexPath, at: .right, animated: true)
    }
    
}

extension MainViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return adArray.count
    }
    
    //collectionView 내부 cell 설정
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let collectionCell = collectionView.dequeueReusableCell(withReuseIdentifier: "mainCustomCell", for: indexPath) as! MainCollectionViewCell
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
