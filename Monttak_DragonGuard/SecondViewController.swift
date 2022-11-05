//
//  SecondViewController.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

//Second화면 ViewController
class SecondViewController: UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.title = "Second"    //네비게이션 타이틀 지정
        self.navigationItem.rightBarButtonItem = nil    //baritem 삭제
        
    }
    
    
    //버튼 클릭 시 세 번째 화면으로 넘어가는 메소드
    @IBAction func Click_Go_ThirdScreen(_ sender: Any) {
        let third = UIStoryboard(name: "Third", bundle: nil)
        
        // UIViewController에서 ThirdViewController로 다운 캐스팅
        let thirdScreen = third.instantiateViewController(withIdentifier: "ThirdScreen") as! ThirdViewController
        
        self.navigationController?.pushViewController(thirdScreen, animated: true) //네비게이션 Controller 전용 넘어가는 구문
    }
    
}

extension SecondViewController: UITableViewDataSource{
    //
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!
        cell.textLabel?.text = "epdl"
        return cell
    }
    
    // 실행 시 몇개의 셀이 생성되는지 작성하는 함수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
}

// tableview 내부 이벤트 발생 시 처리할 수 있는 함수
extension SecondViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //클릭되면 몇번째 데이터가 클릭되었는지 알아내는 함수
        NSLog("\(indexPath.row) 번째 선택됨")
    }
}
