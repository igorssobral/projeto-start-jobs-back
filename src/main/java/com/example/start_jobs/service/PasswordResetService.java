package com.example.start_jobs.service;

import com.example.start_jobs.entity.Usuario;
import com.example.start_jobs.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;


@Service
public class PasswordResetService {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private EmailService emailService;

    public void sendPasswordResetEmail(String email) {
        Optional<Usuario> userOpt = userService.buscarUsuarioPorEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        Usuario user = userOpt.get();
        String token = generateToken();
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

        user.setPasswordResetToken(token);
        user.setPasswordResetExpiration(expirationTime);
        usuarioRepository.save(user);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        // Melhorando o conteúdo do e-mail
        String subject = "Recuperação de Senha";
        String body =
                "<p>Olá,</p>" +
                        "<p>Recebemos uma solicitação para redefinir a sua senha. Se você não fez essa solicitação, por favor, ignore este e-mail.</p>" +
                        "<p>Caso contrário, clique no link abaixo para redefinir sua senha:</p>" +
                        "<p><a href=\"" + resetLink + "\" target=\"_blank\" style=\"color: #1a73e8;\">Clique aqui para redefinir sua senha</a></p>" +
                        "<p>Este link ficará válido por 1 hora. Caso o link expire, você poderá solicitar outra redefinição de senha.</p>" +
                        "<p>Se você tiver alguma dúvida ou precisar de ajuda, não hesite em entrar em contato com nosso suporte.</p>" +
                        "<br>" +
                        "<p>Atenciosamente,</p>" +
                        "<p><strong>Equipe <strong>Start Jobs</strong></p>" +
                        "<p><small>Se você não reconhece esta atividade, entre em contato com nossa equipe imediatamente.</small></p>";

        emailService.sendEmail(email, subject, body);
    }



    public void resetPassword(String token, String newPassword) {
        Optional<Usuario> userOpt = userService.findByPasswordResetToken(token);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Token inválido!");
        }

        Usuario user = userOpt.get();
        if (user.getPasswordResetExpiration().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado!");
        }

        user.setSenha(encryptPassword(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiration(null);
        usuarioRepository.save(user);
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[24];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String encryptPassword(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }
}
