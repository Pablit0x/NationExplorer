//
//  ComposeView.swift
//  iosApp
//
//  Created by Paweł Szymański on 11/12/2023.
//

import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let screenSize = UIScreen.main.bounds.size
        let width = screenSize.width
        return MainKt.MainViewController(screenWidth: width)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
