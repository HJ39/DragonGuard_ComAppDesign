//
//  AppDelegate.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/05.
//

import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    let parsing = Parsing() // 파싱하는 클래스 선언
    
    //앱 실행시 가장 먼저 실행되는 메소드
    func application(_ application: UIApplication, willFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        parsing.getData() // Api 받아오는 부분
        return true
    }

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        Thread.sleep(forTimeInterval: 0.8)  // 로딩화면 띄우고 잠시 멈춤
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

