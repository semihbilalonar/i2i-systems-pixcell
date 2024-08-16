//
//  RoundTextField.swift
//  Trackizer
//
//  Created by Engin Gündüz on 18.12.2023.
//

import SwiftUI

struct RoundTextFieldSignUp: View {
    @State var title: String = "Title"
    @Binding var text: String
    @State var keyBoardType: UIKeyboardType = .default
    var textAlign: Alignment = .center
    var isPassword: Bool = false
    
    var body: some View {
        VStack {
            Text(title)
                .multilineTextAlignment(.leading)
                .font(.customFont(.regular, fontSize: 14))
                .frame(minWidth: 0, maxWidth: .infinity, alignment: textAlign)
                .padding(.horizontal, 20)
                .foregroundColor(.gray50)
                .padding(.bottom)
            if(isPassword) {
                //şifreyi göstermemek için
                SecureField("", text: $text)
                    .padding(15)
                    .frame(height: 40)
                    .overlay{
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(Color.gray70,lineWidth: 1)
                    }
                    .foregroundColor(.white)
                    .background(Color.gray60.opacity(0.05))
                    .cornerRadius(15)
            }else {
                TextField("", text: $text)
                    .padding(15)
                    .keyboardType(keyBoardType)
                    .frame(height: 40)
                    .overlay {
                        RoundedRectangle(cornerRadius:  15)
                            .stroke(Color.gray70, lineWidth: 1)
                    }
                    .foregroundColor(.white)
                    .background(Color.gray60.opacity(0.05))
                    .cornerRadius(15)
            }
        }
    }
}

struct RoundTextFieldSignUp_Previews: PreviewProvider {
    @State static var txt: String = ""
    static var previews: some View {
        RoundTextField(text: $txt)
    }
}
