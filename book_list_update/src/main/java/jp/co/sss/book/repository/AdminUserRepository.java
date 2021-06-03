package jp.co.sss.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.book.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

	AdminUser findByAdminUserIdAndPassword(int userId, String password);
}
