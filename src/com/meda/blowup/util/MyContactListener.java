package com.meda.blowup.util;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.monsters.Monster;

public class MyContactListener implements ContactListener {
	@Override
	public void beginContact(Contact contact) {
		try {
			Fixture a = contact.getFixtureA();
			Fixture b = contact.getFixtureB();
			Object o1 = a.getBody().getUserData();
			Object o2 = b.getBody().getUserData();

			Monster monster1 = null, monster2 = null;
			if (o1 != null) {
				monster1 = (Monster) o1;
			}
			if (o2 != null) {
				monster2 = (Monster) o2;
			}			
			if (monster1!=null && monster2!=null) {
		        if (monster1.isBoss() || monster2.isBoss())
		        	AudioManager.getInstance().playEffect(R.raw.boing);
		        else
		        	AudioManager.getInstance().playEffect(R.raw.squeak);
		    } else {
		    	AudioManager.getInstance().playEffect(R.raw.binglow);
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactimpulse) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
		// TODO Auto-generated method stub
	}
}
