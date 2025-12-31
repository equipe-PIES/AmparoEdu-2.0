package br.com.amparoedu.backend.service;

import br.com.amparoedu.backend.model.DI;
import br.com.amparoedu.backend.repository.DIRepository;
import br.com.amparoedu.backend.repository.EducandoRepository;

import java.util.List;
import java.util.UUID;

public class DIService {

	private final DIRepository diRepo = new DIRepository();
	private final EducandoRepository educandoRepo = new EducandoRepository();

	public boolean cadastrarNovoDI(DI di) throws Exception {

		// Validação se o educando existe
		if (educandoRepo.buscarPorId(di.getEducando_id()) == null) {
			throw new Exception("Educando não encontrado.");
		}

		try {
			String diId = UUID.randomUUID().toString();

			di.setId(diId);
			di.setExcluido(0);
			di.setSincronizado(0);

			diRepo.salvar(di);

			System.out.println("DI cadastrado com sucesso para o educando: " + di.getEducando_id());
			return true;

		} catch (Exception e) {
			System.err.println("Erro ao cadastrar DI: " + e.getMessage());
			return false;
		}
	}

	public boolean atualizarDI(DI di) throws Exception {

		if (diRepo.buscarPorId(di.getId()) == null) {
			throw new Exception("DI não encontrado.");
		}

		try {
			di.setSincronizado(0);
			diRepo.atualizar(di);

			System.out.println("DI atualizado com sucesso: " + di.getId());
			return true;

		} catch (Exception e) {
			System.err.println("Erro ao atualizar DI: " + e.getMessage());
			return false;
		}
	}

	public boolean excluirDI(String id) throws Exception {

		if (diRepo.buscarPorId(id) == null) {
			throw new Exception("DI não encontrado.");
		}

		try {
			diRepo.excluir(id);
			System.out.println("DI excluído com sucesso: " + id);
			return true;

		} catch (Exception e) {
			System.err.println("Erro ao excluir DI: " + e.getMessage());
			return false;
		}
	}

	public DI buscarPorId(String id) {
		try {
			return diRepo.buscarPorId(id);
		} catch (Exception e) {
			System.err.println("Erro ao buscar DI: " + e.getMessage());
			return null;
		}
	}

	public List<DI> buscarPorEducando(String educandoId) {
		try {
			return diRepo.buscarPorEducando(educandoId);
		} catch (Exception e) {
			System.err.println("Erro ao buscar DIs do educando: " + e.getMessage());
			return null;
		}
	}

	public List<DI> buscarNaoSincronizados() {
		try {
			return diRepo.buscarNaoSincronizados();
		} catch (Exception e) {
			System.err.println("Erro ao buscar DIs não sincronizados: " + e.getMessage());
			return null;
		}
	}

	public boolean sincronizarDI(String id) {
		try {
			diRepo.atualizarSincronizacao(id, 1);
			System.out.println("DI sincronizado: " + id);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao sincronizar DI: " + e.getMessage());
			return false;
		}
	}
}
