import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuddysComponent } from './buddys.component';
import { BuddyService } from '../../services/buddyService/buddy.service';
import { TrasferService } from '../../services/transferService/trasfer.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { User } from '../../models/user/user';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('BuddysComponent', () => {
  let component: BuddysComponent;
  let fixture: ComponentFixture<BuddysComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[BuddyService, TrasferService],
      imports: [BuddysComponent, HttpClientTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuddysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get buddy list', () => {
    spyOn(component, 'getBuddyList');
    component.ngOnInit();
    expect(component.getBuddyList).toHaveBeenCalled();
  })

  it('should render buddy table prenom', () => {
    component.dataSource = userList;
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('td');
    els.forEach(element => {
      if(element.innerHTML.includes('Robert'))
        verify = true;
    })

    expect(verify).toBeTrue();
  })

  it('should render buddy table nom', () => {
    component.dataSource = userList;
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('td');
    els.forEach(element => {
      if(element.innerHTML.includes('Lasceaux'))
        verify = true;
    })

    expect(verify).toBeTrue();
  })

  it('should render buddy table mail', () => {
    component.dataSource = userList;
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('td');
    els.forEach(element => {
      if(element.innerHTML.includes('jean@gmail.com'))
        verify = true;
    })

    expect(verify).toBeTrue();
  })

  it('add buddy button', () => {
    spyOn(component, 'addBuddyDialog');
    const addBuddyButton = fixture.debugElement.nativeElement.querySelector('#addBuddy');
    addBuddyButton.click();
    expect(component.addBuddyDialog).toHaveBeenCalled();
  })

  it('transfer button', () => {
    component.dataSource = userList;
    fixture.detectChanges();

    spyOn(component, 'modalTransfer')
    const compiled = fixture.debugElement.nativeElement as HTMLElement;

    const els = compiled.querySelector('#abcdefgh');
    els?.dispatchEvent(new Event('click'));

    expect(component.modalTransfer).toHaveBeenCalled();
  })
});

export const userList: User[]= [
  {
    id: 1,
    idUser: "abcdefgh",
    prenom: "Robert",
    nom: "Dupont",
    email: "robert@gmail.com"
  },
  {
    id: 2,
    idUser: "12345678",
    prenom: "Antoine",
    nom: "Lasceaux",
    email: "antoine@gmail.com"
  },
  {
    id: 3,
    idUser: "zertsdfg",
    prenom: "Jean",
    nom: "Delfort",
    email: "jean@gmail.com"
  }
]
