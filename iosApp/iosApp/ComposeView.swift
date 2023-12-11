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
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
