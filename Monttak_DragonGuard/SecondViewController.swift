//
//  SecondViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Second화면 ViewController
class SecondViewController: UIViewController{
    var testList = testClass().testReturnList()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.title = "Second"    //네비게이션 타이틀 지정
        self.navigationItem.rightBarButtonItem = nil    //barItem 삭제
        
    }
}

extension SecondViewController: UITableViewDataSource{
    // tableview cell들에 입력할 데이터를 관리하는 함수
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!   // table cell을 재사용 큐를 이용하여 화면에 표시
        
        let img = cell.viewWithTag(100) as? UIImageView // 이미지를 표시할 변수
        let title = cell.viewWithTag(101) as? UILabel   // 이름으르 표시할 변수
        let address = cell.viewWithTag(102) as? UILabel // 도로명 주소를 표시할 변수
        
        let row = self.testList[indexPath.row]
        
        title?.text = row.title
        address?.text = row.address
        img?.image = UIImage(named:"monttakpause")
        
        return cell
    }
    
    // 실행 시 몇개의 셀이 생성되는지 작성하는 함수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let listLength = self.testList.count
        return listLength
    }
}

// tableview 내부 이벤트 발생 시 처리할 수 있는 함수
extension SecondViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //클릭되면 몇번째 데이터가 클릭되었는지 알아내는 함수
        NSLog("\(indexPath.row) 번째 선택됨")
        
        let third = UIStoryboard(name: "Third", bundle: nil)
        
        // UIViewController에서 ThirdViewController로 다운 캐스팅
        let thirdScreen = third.instantiateViewController(withIdentifier: "ThirdScreen") as! ThirdViewController
        
        //네비게이션 Controller 전용 넘어가는 구문
        self.navigationController?.pushViewController(thirdScreen, animated: true)
        
        
    }
}
