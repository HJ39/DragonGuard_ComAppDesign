//
//  SecondViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Second화면 ViewController
class SecondViewController: UIViewController {
    var choiceButton: String = "Second" //main화면에서 어떤 버튼을 선택했는지 보여주는 변수
    let screenWidth = UIScreen.main.bounds.size.width // 뷰 전체 폭 길이
    let screenHeight = UIScreen.main.bounds.size.height // 뷰 전체 높이 길이
    var datalist = JejuInfoList().return_Info_List()    //데이터 리스트 불러옴
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //백그라운드를 이미지로 설정
        guard let img = UIImage(named: "배경3") else{ return }
        //이미지크기를 조절해서 백그라운드에 적용
        self.view.backgroundColor = UIColor(patternImage: img.resize(newWidth: screenWidth,newHeight: screenHeight) )
        
        //navigation title 폰트 적용을 위한 코드
        let titleName = UILabel()
        titleName.font = UIFont(name: "OTMogujasusimgyeolB" , size: 25) //목우자심결 폰트 적용
        titleName.text = choiceButton
        
        //네비게이션 아이템 속성 설정 코드
        self.navigationItem.titleView = titleName    //네비게이션 타이틀 지정
        self.navigationItem.rightBarButtonItem = nil    //barItem 삭제
    }
    
}

extension SecondViewController: UITableViewDataSource{
    // tableview cell들에 입력할 데이터를 관리하는 함수
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!   // table cell을 재사용 큐를 이용하여 화면에 표시
        
        //        cell.backgroundColor = UIColor(red: 255/255.0, green: 150/255.0, blue: 100/255.0, alpha: 0.5)    //셀 배경색 설정
        //        tableView.backgroundColor = UIColor(red: 230/255.0, green: 200/255.0, blue: 100/255.0, alpha: 0.5)
        let img = cell.viewWithTag(100) as? UIImageView // 이미지를 표시할 변수
        let title = cell.viewWithTag(101) as? UILabel   // 이름을 표시할 변수
        let address = cell.viewWithTag(102) as? UILabel // 도로명 주소를 표시할 변수
        
        //각 버튼을 section으로 나누어서 section으로 구분해야 함
        //indexPath는 section과 cell의 row의 정보를 가지고 있음
        let row = datalist[indexPath.section]
        
        // =========== 관광지 구별해야함
        
        // 관광지 이름, 도로명 주소 설정
        title?.text = row.title
        address?.text = row.address
        
        // url 이미지 주소 설정하는 코드
        let url = URL(string: datalist[indexPath.section].imgURL ?? "")!
        img?.load(img: img!,url: url,screenWidth: screenWidth)  //휴대폰 기기의 가로, 세로 길이를 넘겨서 비율에 맞게 이미지 표시
        
        tableView.separatorStyle = .none    //셀 구분선 제거
        
        // 셀 모양 설정 하는 코드
        cell.layer.cornerRadius = 40
        cell.layer.shadowRadius = 10
        cell.layer.shadowOpacity = 0.1
        cell.layer.masksToBounds = true
        img?.layer.cornerRadius = 40
        
        return cell
    }
    
    //섹션에 들어갈 String 설정하는 함수
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? { return " " }
    
    // 섹션 개수 설정하는 함수
    func numberOfSections(in tableView: UITableView) -> Int { return datalist.count }
    
    // 실행 시 각 세션에 몇 개의 셀이 생성되는지 작성하는 함수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int { return 1 }
    
    //섹션 간격 설정하는 함수
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat { return 1 }
    
}

// tableview 내부 이벤트 발생 시 처리할 수 있는 함수
extension SecondViewController: UITableViewDelegate{
    //클릭되면 몇번째 데이터가 클릭되었는지 알아내는 함수
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let third = UIStoryboard(name: "Third", bundle: nil)
        // UIViewController에서 ThirdViewController로 다운 캐스팅
        let thirdScreen = third.instantiateViewController(withIdentifier: "ThirdScreen") as! ThirdViewController
        
        // 3번째 화면으로 전송할 데이터 설정하는 코드 부분
        thirdScreen.tourPlaceIndex = indexPath.section
        
        // 버튼 클릭시 navigation방식으로 Third화면 실행
        self.navigationController?.pushViewController(thirdScreen, animated: true)
    }
}

extension UIImage {
    // 배경 설정하는 함수 기기의 가로 세로 길이를 받아와 이미지 리턴
    func resize(newWidth: CGFloat, newHeight: CGFloat) -> UIImage {
        let size = CGSize(width: newWidth, height: newHeight) //이미지 크기 조절
        let render = UIGraphicsImageRenderer(size: size)    // 렌더링을 이용하여 크기 조절
        
        // 지정한 크기만큼 축소시켜서 이미지를 다시 그림
        let renderImage = render.image { context in
            self.draw(in: CGRect(origin: .zero, size: size))
        }
        
        return renderImage
    }
}

extension UIImageView {
    func load(img:UIImageView, url: URL, screenWidth: CGFloat) {
        /**
                이미지 불러올 때 메모리, 디스크 캐시를 탐색하고 없는 경우 네트워크 통신으로 이미지를 불러온다.
         */
        let cacheKey = NSString(string: "\(url)")   //메모리 캐시를 이용하기 위한 캐시key값 설정 여기서는 이미지 url을 사용하였다.
        
        //디스크 캐시에 필요한 변수
        guard let path = NSSearchPathForDirectoriesInDomains(.cachesDirectory, .userDomainMask, true).first else { return }
        var filePath = URL(fileURLWithPath: path)   //파일 경로
        filePath.appendPathComponent(url.lastPathComponent) //이미지 실제 이름을 사용하는 lastPathComponent 사용
        
        //메모리에 캐시된 이미지가 있는 경우
        if let cacheImage = ImageCacheManager.shared.object(forKey: cacheKey){
            img.image = cacheImage
        }
        else if FileManager.default.fileExists(atPath: filePath.path) { //디스크에 이미지가 히트된 경우 실행
            guard let imageData = try? Data(contentsOf: filePath) else { return } //저장된 이미지 Data를 가져옴
            guard let image = UIImage(data: imageData) else { return }  //Data를 UIImage타입으로 변경
            let resizeImg = image.resize(newWidth: screenWidth,newHeight: (screenWidth / 2))
            img.image = resizeImg
        }
        else{ // 메모리 or 디스크에 캐시된 이미지가 없는 경우 네트워크 통신이 이루어진다.
            DispatchQueue.global().async {
                if let data = try? Data(contentsOf: url) {
                    if let image = UIImage(data: data) {
                        DispatchQueue.main.async {
                            //네트워크 통신을 하고 메모리와 디스크에 저장시킴
                            let resizeImg = image.resize(newWidth: screenWidth,newHeight: (screenWidth / 2)) //이미지 크기 조절
                            ImageCacheManager.shared.setObject(resizeImg, forKey: cacheKey) //메모리 캐시에 사용할 수 있게끔 올림
                            //디스크에 저장하기 위해 파일을 만들어서 디스크에 저장
                            if !FileManager.default.fileExists(atPath: filePath.path) {
                                FileManager.default.createFile(atPath: filePath.path, contents: resizeImg.pngData(), attributes: nil)
                                img.image = resizeImg
                            }
                        }
                    }
                }
            }
        }
        
        
    }
}
