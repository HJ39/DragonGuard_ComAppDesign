//
//  ImageCacheManager.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/11.
//

import Foundation
import UIKit

class ImageCacheManager{    //메모리 캐시를 이용하기 위한 클래스
    static let shared = NSCache<NSString , UIImage>()   
    private init() {}
}
