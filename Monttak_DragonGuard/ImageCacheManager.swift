//
//  ImageCacheManager.swift
//  Monttak_DragonGuard
//
//  Created by 정호진 on 2022/11/11.
//

import Foundation
import UIKit

class ImageCacheManager{
    static let shared = NSCache<NSString , UIImage>()
    private init() {}
}
