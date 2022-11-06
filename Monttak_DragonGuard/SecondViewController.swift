//
//  SecondViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Second화면 ViewController
class SecondViewController: UIViewController {
    var testList = testClass().testReturnList()
    var choiceButton: String = "Second"
    
    // 뷰 전체 폭 길이
    let screenWidth = UIScreen.main.bounds.size.width
    // 뷰 전체 높이 길이
    let screenHeight = UIScreen.main.bounds.size.height
    
    override func viewDidLoad() {
        super.viewDidLoad()
        guard let img = UIImage(named: "배경1") else{ return }
        
//        // 이미지 크기 조절
//        let imgSize = CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight)
//        img.draw(in: imgSize)
        
        self.view.backgroundColor = UIColor(patternImage: img )
        self.navigationItem.title = choiceButton    //네비게이션 타이틀 지정
        self.navigationItem.rightBarButtonItem = nil    //barItem 삭제
        self.navigationController?.navigationBar.backgroundColor = UIColor(red: 230/255.0, green: 200/255.0, blue: 100/255.0, alpha: 0.5)
    }
}

extension SecondViewController: UITableViewDataSource{
    // tableview cell들에 입력할 데이터를 관리하는 함수
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!   // table cell을 재사용 큐를 이용하여 화면에 표시
//        cell.backgroundColor = UIColor(red: 255/255.0, green: 150/255.0, blue: 100/255.0, alpha: 0.5)    //셀 배경색 설정
//        tableView.backgroundColor = UIColor(red: 230/255.0, green: 200/255.0, blue: 100/255.0, alpha: 0.5)
        tableView.separatorStyle = .none
        
        let img = cell.viewWithTag(100) as? UIImageView // 이미지를 표시할 변수
        let title = cell.viewWithTag(101) as? UILabel   // 이름으르 표시할 변수
        let address = cell.viewWithTag(102) as? UILabel // 도로명 주소를 표시할 변수
        
        let row = self.testList[indexPath.row]
        
        title?.text = row.title
        address?.text = row.address
        img?.image = UIImage(named:"삼겹살")
        
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
    func numberOfSections(in tableView: UITableView) -> Int { return self.testList.count }
    
    // 실행 시 각 세션에 몇 개의 셀이 생성되는지 작성하는 함수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int { return 1 }
    
    //섹션 간격 설정하는 함수
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat { return 1 }
    
    
    
}

// tableview 내부 이벤트 발생 시 처리할 수 있는 함수
extension SecondViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //클릭되면 몇번째 데이터가 클릭되었는지 알아내는 함수
        NSLog("\(indexPath.row) 번째 선택됨")
        
        let third = UIStoryboard(name: "Third", bundle: nil)
        // UIViewController에서 ThirdViewController로 다운 캐스팅
        let thirdScreen = third.instantiateViewController(withIdentifier: "ThirdScreen") as! ThirdViewController
        
        // 버튼 클릭시 navigation방식으로 Third화면 실행
        self.navigationController?.pushViewController(thirdScreen, animated: true)
        
    }
}
